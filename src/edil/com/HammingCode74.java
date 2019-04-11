package edil.com;

import java.util.concurrent.ThreadLocalRandom;

public class HammingCode74 {

    private String original;
    private String encoded;
    private String encodedWithErrors;
    private String correctedCode;
    private String decoded;
    private int originalLength;
    private int encodedLength;


    public HammingCode74(String s) {
        original = s;
        originalLength = s.length();
        encoded = "";
        encodedWithErrors = "";
        correctedCode = "";
        decoded ="";
        encodeString();
        encodedLength = encoded.length();
        addErrors();
        correctCoding();
        decodeString();
    }

    public String getEncodedWithErrors() {
        return encodedWithErrors;
    }

    public String getOriginal() {
        return original;
    }

    public String getEncoded() {
        return encoded;
    }

    public int getOriginalLength() {
        return originalLength;
    }

    public int getEncodedLength() {
        return encodedLength;
    }

    public String getCorrectedCode() {
        return correctedCode;
    }

    public String getDecoded() {
        return decoded;
    }

    private String encodeBlock(String s){

        String ans = "";
        int c1 = if_is_one(s.charAt(0));
        int c2 = if_is_one(s.charAt(1));
        int c3 = if_is_one(s.charAt(2));
        int c4 = if_is_one(s.charAt(3));
        int p1 = c1^c2^c4;
        int p2 = c1^c3^c4;
        int p3 = c2^c3^c4;

        ans = ""+p1+p2+c1+p3+c2+c3+c4;

        return ans;
    }

    private void encodeString(){

        int blockNum = originalLength /4;
        String lastSubString = original.substring(blockNum * 4);
        for(int i=0; i< blockNum * 4; i = i + 4){

            this.encoded += encodeBlock(original.substring(i,i+4)) ;

        }

        this.encoded += lastSubString;

    }

    private void addErrors(){

        int blockNum = encodedLength /7;
        String lastSubString = encoded.substring(blockNum * 7);
        for(int i=0; i< blockNum * 7; i = i + 7){
            String subString = encoded.substring(i,i+7);
            int randomNum = ThreadLocalRandom.current().nextInt(0, 7);
            char bit = '0';
            if(if_is_one(subString.charAt(randomNum)) == 0)
                bit = '1';


            encodedWithErrors = encodedWithErrors + subString.substring(0,randomNum)+bit+subString.substring(randomNum + 1);


        }

        encodedWithErrors += lastSubString;

    }

    private int if_is_one(char c){

        if(c == '1')
            return 1;
        else
            return 0;

    }

    private void correctCoding(){

        int blockNum = encodedLength /7;
        String lastSubString = encoded.substring(blockNum * 7);
        for(int i=0; i< blockNum * 7; i = i + 7){

            correctedCode = correctedCode + correctBlock(encodedWithErrors.substring(i,i+7)) ;

        }

        correctedCode += lastSubString;

    }

    private String correctBlock(String s){
        int d1 = Integer.parseInt(s.charAt(2) + "") ;
        int d2 = Integer.parseInt(s.charAt(4) + "") ;
        int d3 = Integer.parseInt(s.charAt(5) + "") ;
        int d4 = Integer.parseInt(s.charAt(6) + "") ;
        int p1 = Integer.parseInt(s.charAt(0) + "") ;
        int p2 = Integer.parseInt(s.charAt(1) + "") ;
        int p3 = Integer.parseInt(s.charAt(3) + "") ;
        boolean pair1 = true;
        boolean pair2 = true;
        boolean pair3 = true;

        if((d1^d2^d4) != p1){
            pair1 = false;
        }
        if((d1^d3^d4) != p2){
            pair2 = false;
        }
        if((d2^d3^d4) != p3){
            pair3 = false;
        }

        if(!pair1 && !pair2 && pair3){
            d1 ^= 1;
        }
        if(!pair1 && pair2 && !pair3){
            d2 ^= 1;
        }
        if(pair1 && !pair2 && !pair3){
            d3 ^= 1;
        }
        if(!pair1 && !pair2 && !pair3){
            d4 ^= 1;
        }

        if(!pair1 && pair2 && pair3){
            p1 ^= 1;
        }
        if(pair1 && pair2 && !pair3){
            p3 ^= 1;
        }
        if(pair1 && !pair2 && pair3){
            p2 ^= 1;
        }
        if(pair1 && pair2 && pair3){
            return s;
        }

        return ""+p1+p2+d1+p3+d2+d3+d4;
    }

    private void decodeString(){

        int blockNum = encodedLength /7;
        String lastSubString = encoded.substring(blockNum * 7);
        for(int i=0; i< blockNum * 7; i = i + 7){

            String subString = correctedCode.substring(i,i+7);
            decoded = decoded + subString.substring(2,3)+subString.substring(4);


        }

        decoded += lastSubString;
    }


}
