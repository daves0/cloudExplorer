/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.package
 * cloudExplorer
 *
 */
package cloudExplorer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteVersionRequest;
import static cloudExplorer.NewJFrame.jTextArea1;
import com.amazonaws.ClientConfiguration;

public class Delete implements Runnable {

    String message = null;
    NewJFrame mainFrame;
    String what = null;
    String access_key = null;
    String bucket = null;
    String endpoint = null;
    String secret_key = null;
    String destination = null;
    Thread del;
    String version = null;

    public void calibrate() {
        try {
            jTextArea1.setCaretPosition(jTextArea1.getLineStartOffset(jTextArea1.getLineCount() - 1));
        } catch (Exception e) {
        }
    }

    Delete(String Awhat, String Aaccess_key, String Asecret_key, String Abucket, String Aendpoint, String Aversion) {
        what = Awhat;
        access_key = Aaccess_key;
        secret_key = Asecret_key;
        bucket = Abucket;
        endpoint = Aendpoint;
        version = Aversion;
    }

    public void run() {
        AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
        AmazonS3 s3Client = new AmazonS3Client(credentials,
                new ClientConfiguration().withSignerOverride("S3SignerType"));
        s3Client.setEndpoint(endpoint);

        try {

            if (version != null) {
                s3Client.deleteVersion(new DeleteVersionRequest(bucket, what, version));
            } else {
                s3Client.deleteObject(new DeleteObjectRequest(bucket, what));
            }
            NewJFrame.jTextArea1.append("\nDeleted object: " + what);
            calibrate();
        } catch (Exception Delete) {
            NewJFrame.jTextArea1.append("\n\nAn error has occurred in DeleteFile.");
            NewJFrame.jTextArea1.append("\n\nError Message:    " + Delete.getMessage());
            message = message + "\n" + Delete.getMessage();
            calibrate();
        }
        calibrate();
    }

    void startc(String Awhat, String Aaccess_key, String Asecret_key, String Abucket, String Aendpoint, String Aversion) {
        del = new Thread(new Delete(Awhat, Aaccess_key, Asecret_key, Abucket, Aendpoint, Aversion));
        del.start();
        try {
            del.join();
        } catch (Exception Delete) {
        }
    }

    void stop() {
        del.stop();
        mainFrame.jTextArea1.setText("\nDownload compelted or aborted. Please click search to refresh the Object Explorer if needed.\n");
    }

}
