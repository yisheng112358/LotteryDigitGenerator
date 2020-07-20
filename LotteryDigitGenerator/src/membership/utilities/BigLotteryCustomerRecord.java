package membership.utilities;

public class BigLotteryCustomerRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int recordid;
	private String lotterycustomer;
	private String lotterynumbers;
	private String retrievedate;

	public BigLotteryCustomerRecord() {
		super();
	}

	public BigLotteryCustomerRecord(String lotterycustomer, String lotterynumbers) {
		super();
		this.lotterycustomer = lotterycustomer;
		this.lotterynumbers = lotterynumbers;
	}

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}

	public String getLotterycustomer() {
		return lotterycustomer;
	}

	public void setLotterycustomer(String lotterycustomer) {
		this.lotterycustomer = lotterycustomer;
	}

	public String getLotterynumbers() {
		return lotterynumbers;
	}

	public void setLotterynumbers(String lotterynumbers) {
		this.lotterynumbers = lotterynumbers;
	}

	public String getRetrievedate() {
		return retrievedate;
	}

	public void setRetrievedate(String retrievedate) {
		this.retrievedate = retrievedate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
