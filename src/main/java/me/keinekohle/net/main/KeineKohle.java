package me.keinekohle.net.main;

import me.keinekohle.net.license.ServerIdentifier;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class KeineKohle extends JavaPlugin {

    //message prefix
    public static String prefix = "§8[§bDungeon§8]§e ";

    //server identifier
    public static String serverIdentifier = ServerIdentifier.getServerIdentifier();

    //loaded on startup
    @Override
    public void onEnable() {

       /* try {

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();

            KeyPair keyPair = pair;

            byte[] data = serverIdentifier.getBytes("UTF8");

            Signature sig = Signature.getInstance("SHA256WithRSA");
            sig.initSign(keyPair.getPrivate());
            sig.update(data);
            byte[] signatureBytes = sig.sign();
            System.out.println("Signature:" + Base64.getEncoder().encode(signatureBytes));

            sig.initVerify(keyPair.getPublic());
            sig.update(data);

            System.out.println(sig.verify(signatureBytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        */
    }
}
