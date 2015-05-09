package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {
	
	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface menjacnica;
	
	/**
	 * Startovanje apliacije iz GUIKontrolera, a ne iz glavne forme.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					glavniProzor = new MenjacnicaGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(glavniProzor);
		prozor.setLocationRelativeTo(glavniProzor.getContentPane());
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(glavniProzor, valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		if (valuta != null) {
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(glavniProzor,
					valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceni, int sifra, 
			double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceni);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			menjacnica.dodajValutu(valuta);
			glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());;
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void izvrsiZamenu(Valuta valuta, JRadioButton prodaja, JTextField iznos, JTextField konacniIznos2){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,
							prodaja.isSelected(), 
							Double.parseDouble(iznos.getText()));
		
			konacniIznos2.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
}
