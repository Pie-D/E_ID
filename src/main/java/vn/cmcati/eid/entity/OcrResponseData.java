package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "ocr_response_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OcrResponseData extends ResponseDataBase{
    private String address;
    private String backImage;
    private Integer birthDay;
    private Integer birthMonth;
    private String birthString;
    private Integer birthYear;
    private String cardCroppedImage;
    private Integer cardHeight;
    private String cardImage;
    private String cardNo;
    private String cardOcrBlurs;
    private Double cardOcrCut;
    private Double cardOcrDevice;
    private Double cardOcrGlare;
    private Double cardOcrOcclusion;
    private Integer cardOcrPrint;
    private Integer cardWidth;
    private String dacDiem;
    private String danToc;
    private String expiredDate;
    private String faceCroppedImage;
    private String faceImage;
    private String fullName;
    private String gender;
    private String hang;
    private String idCccd;
    private String idCccdHc;
    private String idHc;
    private String loaiHc;
    private String masoHc;
    private String mrz;
    private String nationality;
    private String ngayCapHc;
    private String ngayHetHc;
    private String ngayTrung;
    private String noiCapBlx;
    private String noiCapCccd;
    private String noiCapCmnd;
    private String noiCapHc;
    private String noiSinhHc;
    private String placeOfBirth;
    private String publishDate;
    private String religion;
    private Double similarity;
    private String thuongTruBlx;
}
