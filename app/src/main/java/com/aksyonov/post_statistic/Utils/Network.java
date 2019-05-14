package com.aksyonov.post_statistic.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Network {

    private static final String API_BASE_URL ="https://api.inrating.top/v1/users/posts/";

    public static final String LIKERS ="likers/all";
    public static final String COMMENTATORS ="commentators/all";
    public static final String MENTIONS ="mentions/all";
    public static final String REPOSTERS ="reposters/all";
    public static final String POST ="get";

    private static final String PARAM_ID ="id";
    private static final String TEST_ID ="2720";
    private  static  final String ACCESS ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJmNGU5ZDA1MzU3MDI3MmFlMGZhZTMzM2Y4ZTY4ZWVlMWNiMzc0NmM0Mjg5NzI0ZTExNzJjM2Q4ODYzNDNkNDkyY2ZjZjI4Njg0NzQ0MGEwIn0.eyJhdWQiOiIyIiwianRpIjoiMmY0ZTlkMDUzNTcwMjcyYWUwZmFlMzMzZjhlNjhlZWUxY2IzNzQ2YzQyODk3MjRlMTE3MmMzZDg4NjM0M2Q0OTJjZmNmMjg2ODQ3NDQwYTAiLCJpYXQiOjE1MzY4MzE4ODcsIm5iZiI6MTUzNjgzMTg4NywiZXhwIjoxNTY4MzY3ODg3LCJzdWIiOiIzOCIsInNjb3BlcyI6W119.dRitRnoqNFS3xUgtLdLiDjDVVe7ZFNrh24Qm2ML9m-V7kZpgQgajArYoS44kMa1dz_MHUhq3pqk8SnAYIsULgfrOvewTUzmH1C92-yL64Uqnv7lqWizldX2fbJ2IbB8khOCtQ-CCNA_fGY_zEBJXLsOqr4Z00tbZE6fa0PX4Mu0SsuUakLeygXbXnKOmFyZmLJZWoXKpbqiSBU239nrcyqJftBon8DL1BAUuFiadap-gpVSXj8h6BX-FsJx5cgPHFiijIalcEgzOq4VCMkwbQE8xbTsmmxkZUOnM7oKab5inzl8EV5iUgcExeSbHT6k_phOkA7XUaR6PhVoKrSQTPcfdijhME1IHfPVDPGO0vhd6hKszRrhjEPEpoothBoB8ss0lmuCFURdxFv17q97rfpDn1OfO_Y3wYuRW2lqFAnw7sLd92CHjfONwQKswLDzwE4hiQhB8iS_UEbuL_UamNOiCLfjNnVWbVc9BvoReEa8jG4coc0Kv9VNJVWh3D_hGf8dLRZBd1a7zB6-nSpKGf0eAzB0_rBXsyBepjudC-5EFDjloJOxy1Mdruoq6mQa_tFcO99JRteUSd0CXHZO-CN4Bp4xND9kstdutjBn2UWT5xhNq_QRBmBsBDAwp647dUCyQofutN9GUlu2LxmhL0ojydazdND_d9rHtY9t-ndw";


    public static URL generateURL ( String method) {
       Uri built=Uri.parse(API_BASE_URL + method ).buildUpon()
               .appendQueryParameter(PARAM_ID, TEST_ID)
               .build();
        //Uri built=Uri.parse("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5").buildUpon().build();


        URL url= null;
        try {
             url = new URL(built.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    return url;

    }

    public  static String getResponceFromURL (URL url) throws IOException {
        HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Authorization", ACCESS);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);


        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scaner = new Scanner(in);
            scaner.useDelimiter("\\A");

            boolean hasInput = scaner.hasNext();

            if (hasInput) {
                return scaner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
