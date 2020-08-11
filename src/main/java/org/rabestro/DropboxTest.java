package org.rabestro;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DropboxTest {
    private static final String ACCESS_TOKEN = "R9xqS86D9FAAAAAAAAAAAdyFxUqSQra1TNooiisg4CKe3zuflcQAZb-VsdBT3EcU";

    public static void main(String[] args) throws DbxException, IOException {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        final var path = "/home/jegors/Pictures/Java_IQ.png";
        InputStream in = new FileInputStream(path);
        client.files()
                .uploadBuilder("/Java_IQ.png")
                .uploadAndFinish(in);

    }
}
