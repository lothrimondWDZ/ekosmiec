package pl.ekosmiec.entities;

public class WasteType {

            private int id;
            private String nazwa;
            private float przelicznik = 1;
            private String opis;
            
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public String getNazwa() {
				return nazwa;
			}
			public void setNazwa(String nazwa) {
				this.nazwa = nazwa;
			}
			public float getPrzelicznik() {
				return przelicznik;
			}
			public void setPrzelicznik(float przelicznik) {
				this.przelicznik = przelicznik;
			}
			public String getOpis() {
				return opis;
			}
			public void setOpis(String opis) {
				this.opis = opis;
			}
            
            

}
