package vn.cmcati.eid.models;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.enums.Language;
import vn.cmcati.eid.enums.RequestType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest extends Request {
     String orderInfo;
     long amount;
     String partnerName;
     String subPartnerCode;
     RequestType requestType;
     String redirectUrl;
     String ipnUrl;
     String storeId;
     String extraData;
     String partnerClientId;
     Boolean autoCapture = true;
     Long orderGroupId;
     String signature;

    public PaymentRequest(String partnerCode, String orderId, String requestId, Language lang, String orderInfo, long amount, String partnerName, String subPartnerCode, RequestType requestType, String redirectUrl, String ipnUrl, String storeId, String extraData, String partnerClientId, Boolean autoCapture, Long orderGroupId, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.orderInfo = orderInfo;
        this.amount = amount;
        this.partnerName = partnerName;
        this.subPartnerCode = subPartnerCode;
        this.requestType = requestType;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.storeId = storeId;
        this.extraData = extraData;
        this.partnerClientId = partnerClientId;
        this.autoCapture = autoCapture;
        this.orderGroupId = orderGroupId;
        this.signature = signature;
    }
    public String getRequestType() {
        return requestType.getRequestType();
    }
}
