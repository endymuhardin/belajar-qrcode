# Generate QR Code #

Cara menggunakan :

1. Buka file `GenerateQRCode.java`

2. Edit variabel berikut bila perlu : 
   
    * `productCode`
    * `productionYear`
    * `productNumberLength`
    * `productQuantity`

3. Compile dan jalankan aplikasinya

    ```
    mvn clean compile exec:java -Dexec.mainClass="com.muhardin.endy.belajar.qrcode.GenerateQRCode"
    ```

4. QR Code ada di folder `target/qrcode`