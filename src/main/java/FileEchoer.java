import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.List;
import java.util.Map;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreInputStream;



public class FileEchoer extends HttpServlet {
  
 	 private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  
  
  	 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        	response.setContentType("text/plain");
      	response.getWriter().println("AAAAAAAAAAAA");
         if (ServletFileUpload.isMultipartContent(request)) {
           	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
       	 	List<BlobKey> blobKeys = blobs.get("file");
            BlobstoreInputStream blobStream = new BlobstoreInputStream(blobKeys.get(0));
            InputStreamReader stream = new InputStreamReader(blobStream);
            BufferedReader reader = new BufferedReader(stream);

        		if (blobKeys == null || blobKeys.isEmpty()) {
            	response.sendRedirect("/");
            } else {
              	String line;
              	while ((line = reader.readLine()) != null) {
						response.setContentType("text/plain");
      				response.getWriter().println(line);
					}
            }
   	 }
	}
}