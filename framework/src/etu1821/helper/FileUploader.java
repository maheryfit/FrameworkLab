package etu1821.helper;

/**
 * FileUploader
 */
public class FileUploader {
   private String filename;
   private String path;
   private byte[] file;

   /**
    * 
    * @param filename
    * @param path
    * @param file
    */
   public FileUploader(String filename, String path, byte[] file) {
      setFilename(filename);
      setPath(path);
      setFile(file);
   }

   FileUploader() {
      setFilename(null);
      setPath(null);
      setFile(null);
   }

   public String getFilename() {
      return filename;
   }

   public String getPath() {
      return path;
   }

   public byte[] getFile() {
      return file;
   }

   /**
    * 
    * @param filename
    */
   public void setFilename(String filename) {
      this.filename = filename;
   }

   /**
    * 
    * @param path
    */
   public void setPath(String path) {
      this.path = path;
   }

   /**
    * 
    * @param file
    */
   public void setFile(byte[] file) {
      this.file = file;
   }
}