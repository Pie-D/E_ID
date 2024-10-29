package vn.cmcati.eid.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse extends Response {
    public PaymentResponse(Integer resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

     String requestId;

     Long amount;

     String payUrl;

     String shortLink;

     String deeplink;

     String qrCodeUrl;

     String deeplinkWebInApp;

     Long transId;

     String applink;

     String partnerClientId;

     String bindingUrl;

     String deeplinkMiniApp;
}
