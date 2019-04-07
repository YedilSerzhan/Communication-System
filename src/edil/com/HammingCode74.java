package edil.com;

public class HammingCode74 {

    String original;
    String encoded;

    public HammingCode74() {
    }

    public HammingCode74(String original) {
        this.original = original;
    }

    String encodeBlock(String s){

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

    String encodeString(String s){

        String encoded = "";
        int n = s.length();
        int blockNum = n /4;
        String lastSubString = s.substring(blockNum * 4);
        for(int i=0; i< blockNum * 4; i = i + 4){

            encoded += encodeBlock(s.substring(i,i+4)) ;

        }

        encoded += lastSubString;



        return encoded;
    }

    int if_is_one(char c){

        if(c == '1')
            return 1;
        else
            return 0;

    }

}
