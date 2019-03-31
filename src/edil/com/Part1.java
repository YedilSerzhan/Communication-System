package edil.com;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import edil.com.HuffmanCode;

public class Part1 {
    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);


    }

    public static void main(String[] args) throws IOException {

        // map for char & int
        HashMap<Character,Integer> charNums = new HashMap<Character, Integer>();

        //map for char & double
        HashMap<Character,Double> charDouble = new HashMap<Character, Double>();


        //open the input file, save as a string
        String s = readFile("/Users/edil/Desktop/text.txt", StandardCharsets.UTF_8);

        //get the length of the string
        int n = s.length();

        //read and count each character from string
        for (int i = 0; i < n; i++) {

            char c = s.charAt(i);

            if(!charNums.containsKey(c)){

                charNums.put(c,1);
            }else{

                charNums.put(c, charNums.get(c) + 1);
            }
        }

//
//        DecimalFormat df = new DecimalFormat("#.####");
//        df.setRoundingMode(RoundingMode.CEILING);

        for (Map.Entry<Character, Integer> entry : charNums.entrySet()) {
            char c = entry.getKey();
            double num = entry.getValue();
            charDouble.put(c, num * 1.0/n);
            System.out.println(c+": " + charDouble.get(c));
        }


        HuffmanCode huffmanCode = new HuffmanCode(s);

        HashMap<Character,String> res = new HashMap<Character, String>();
        res = huffmanCode.getCompressedResult();

        for (Character c : res.keySet()) {
            System.out.println(c+": "+ res.get(c));
        }


    }
}
