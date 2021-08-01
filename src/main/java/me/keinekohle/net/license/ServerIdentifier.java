package me.keinekohle.net.license;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerIdentifier {

    public static String getServerIdentifier()  {
        try {
            return encode(generateServerIdentifier());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateServerIdentifier() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String processorIdentifier = centralProcessor.getIdentifier();
        String processors = String.valueOf(centralProcessor.getLogicalProcessorCount());

        return processorSerialNumber+vendor+processors+processorIdentifier;
    }

    private static String encode(String stringToEncode) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(stringToEncode.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return stringBuilder.toString();
    }

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
