package com.w3engineers.core.snacksready.data.local.appconst;

import android.os.Environment;

import java.io.File;

/*
*  ****************************************************************************
*  * Created by : Ahmed Mohmmad Ullah (Azim) process 10/17/2017 at 10:29 AM.
*  * Email : azim@w3engineers.com
*  * 
*  * Last edited by : Md. Hasnain 04/10/2018.
*  * New directory added for download folder
*  * 
*  * Last Reviewed by : <Reviewer Name> process <mm/dd/yy>
*  ****************************************************************************
*/
public interface DirConst {

    String EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    String ROOT_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/";
    String ROOT_DIRECTORY_DOWNLOAD = EXTERNAL_STORAGE_DIRECTORY + "/Download/";
    String AVATAR_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/.avatar/";
    String FILE_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/file/";
    String THUMB_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/.thumb/";
    String PHOTO_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/image/";
    String VOICE_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/voice/";
    String TEMP_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/.temp/";
    String CAMERA_DIRECTORY = EXTERNAL_STORAGE_DIRECTORY + "/MShare/capture/";

    String MY_AVATAR_LARGE ="my_profile_large.jpg";
    String MY_AVATAR_THUMB = "my_profile_thumb.jpg";
    String IMAGE = "MShare_IMG";
    String VOICE = "MShare_VOICE";

}
