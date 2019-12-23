package com.saurav;

import java.awt.*;

public enum DataProcessingState {
    PARSED("Document Parsed, Not uploaded yet", new Color(189,122,0)),
    PARSING_FAILED("Failed while parsing the document", Color.red),
    UPLOADED_TO_DB(" rows uploaded to database", new Color(23, 121, 23)),
    DB_UPLOAD_ERROR("Error while uploading to database", Color.red),
    WRONG_PASSWORD("Wrong document password", Color.red),
    DEFAULT_ERROR("Error uploading pdf", Color.red);

    DataProcessingState(String explanation, Color isSuccess) {
        this.explanation = explanation;
        this.isSuccess = isSuccess;
    }

    String explanation;
    Color isSuccess;
}
