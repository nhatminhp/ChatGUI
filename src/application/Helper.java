package application;

public class Helper {
    public String removeDoubleCode(String string) {
        if (string.equals("null")) return "";
        return string.replace("\"", "");
    }
}
