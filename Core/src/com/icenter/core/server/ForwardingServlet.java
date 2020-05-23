package com.icenter.core.server;

import com.icenter.core.client.primitive.Strings;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class ForwardingServlet extends HttpServlet {

    private static final long serialVersionUID = 8249982779308108466L;
    private String destinationUrlPrefix = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        destinationUrlPrefix = config.getInitParameter("destinationUrlPrefix");
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] body = printAndGetBody(req);
        URL target  = getTargetURL(req);

        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) target.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(body.length));
            conn.setRequestProperty("Content-Type",  req.getContentType());

            IOUtil.writeAndClose(conn.getOutputStream(), body);

            int responseCode = conn.getResponseCode();
            InputStream in = null;
            try {
                in = conn.getInputStream();
            }
            catch (Exception error) {
                in = conn.getErrorStream();
            }

            if (in == null) {
                throw new ServletException("Unexpected http code:" + target.toString());
            }
            else {
                copyHeaders(resp, conn);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                IOUtil.copyAndClose(in, buffer);

                if (!Strings.of(conn.getHeaderField("Content-Type")).contains("application/octet-stream")) {
                    System.out.println("\n");
                    System.out.println(new String(buffer.toByteArray()));
                    System.out.println("\n");
                }

                IOUtil.copyAndClose(new ByteArrayInputStream(buffer.toByteArray()), resp.getOutputStream());
            }
        }
        catch (Exception error) {
            error.printStackTrace();
            throw new ServletException(error);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void copyHeaders(HttpServletResponse resp, HttpURLConnection conn) {
        for (int i = 0;; i++) {
            String headerName = conn.getHeaderFieldKey(i);
            String headerValue = conn.getHeaderField(i);

            if (headerName == null && headerValue == null) { // no more
                break;
            }
            resp.setHeader(Strings.of(headerName), Strings.of(headerValue));
        }
    }

    public void print(HttpServletRequest request) throws IOException {

        StringBuffer out = new StringBuffer();
        out.append("Request URL:" + getTargetURL(request) + '\n');
        out.append("Method:" + request.getMethod() + '\n');

        Enumeration<?> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String header = (String) names.nextElement();
            String value = request.getHeader(header);
            out.append(header + ": " + value + '\n');
        }

        System.out.println(out.toString());
    }

    public byte[] printAndGetBody(HttpServletRequest request) throws IOException {
        byte[] body = IOUtil.read(request.getInputStream());
        print(request);

        String bodyStr = new String(body, "UTF-8");
        System.out.println(bodyStr.substring(0, Math.min(64000, bodyStr.length())) + "\n");
        return body;
    }

    private URL getTargetURL(HttpServletRequest req) throws IOException {
        String pathInfo = req.getPathInfo();
        String paramString = req.getQueryString();
        String suffix = pathInfo == null ? "" : pathInfo;
        String fullURL = destinationUrlPrefix + suffix;
        if (paramString != null && paramString.length() > 0) {
            fullURL += "?" + paramString;
        }
        return getURL(fullURL);
    }

    public URL getURL(String path) throws IOException {

        /**
         * Short-circuit if resource path is null
         */
        if (path == null) {
            throw new FileNotFoundException("Requested resource was null (passed in null)");
        }

//		if (path.endsWith("/") || path.endsWith("\\") || path.endsWith(".")) {
//			throw new MalformedURLException("Path may not end with a slash or dot");
//		}

        if (path.indexOf("..") != -1) {
            throw new MalformedURLException("Path may not contain double dots");
        }

        String upperResource = path.toUpperCase();
        if (upperResource.indexOf("/WEB-INF") > -1 ||
                upperResource.indexOf("/META-INF") > -1) {
            throw new MalformedURLException("Path may not contains /WEB-INF or /META-INF");
        }

        if (upperResource.endsWith(".JSP")) {
            throw new MalformedURLException("Path may not end with .jsp");
        }

        if (upperResource.startsWith("HTTP")) {
            return new URL(path);
        }

        URL url = getServletContext().getResource(path);
        if (url == null) {
            throw new FileNotFoundException("Requested resource was null (" + path + ")");
        }
        return url;
    }

    public static String join(String glue, Collection<String> stringCollection) {

        if (stringCollection == null)
            throw new NullPointerException();

        if (stringCollection.size() == 0) {
            return "";
        }

        Iterator<String> iter = stringCollection.iterator();

        StringBuffer buf = new StringBuffer(100);
        buf.append(iter.next().toString());

        while(iter.hasNext()) {
            String next = iter.next();
            if (next == null)
                continue;
            if (glue != null)
                buf.append(glue);
            buf.append(next);
        }

        return buf.toString();
    }
}
