package service;

import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class Cryptography {

    public static String computeAlgorithmSha256(String input){
        SHA256.Digest digestSHA256 = new SHA256.Digest();
        byte[] digest = digestSHA256.digest(input.getBytes());

        return Hex.toHexString(digest);
    }
    
    public static String computeAlgorithmSha3(String input){
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(input.getBytes());

        return Hex.toHexString(digest);
    }
}
