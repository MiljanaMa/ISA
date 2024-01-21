package medequipsystem.dto;

import medequipsystem.domain.enums.ReservationStatus;

public class QRCodeDTO {
    private byte[] qrCode;
    private ReservationStatus status;

    public QRCodeDTO(){

    }
    public QRCodeDTO(byte[] qrCode, ReservationStatus status){
        this.qrCode = qrCode;
        this.status = status;
    }
    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
