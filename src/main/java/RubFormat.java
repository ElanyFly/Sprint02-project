public class RubFormat {
    public static String getRub(double price) {
        double excPrice = Math.floor(price) % 100;
        double modulPrice = Math.floor(price) % 10;

        if (excPrice == 11 || excPrice == 12 || excPrice == 13 || excPrice == 14) {
            return "рублей";
        } else if (modulPrice == 1) {
            return "рубль";
        } else if (modulPrice > 1 && modulPrice < 5) {
            return "рубля";
        } else {
            return "рублей";
        }
    }
}
