package cm.belrose.jwtspringsecurity.entities;

public class UploadFileResponse {

    private String fileName;
    public UploadFileResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
