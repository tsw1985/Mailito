package dad.mailito;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import dad.mailito.utils.Mensaje;

public class EmailTableModel implements TableModel {
	
	private InboxMail inbox; 
	
	public EmailTableModel(InboxMail inbox){
		super();
		this.inbox = inbox;
	}
	public EmailTableModel()
	{
		super();
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch ( columnIndex ){
			case 0: return String.class;
			case 1: return String.class;
			case 2: return String.class;
		}

		return null;
	}

	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
	
		switch ( columnIndex ){
			case 0: return "Asunto";
			case 1: return "Fecha";
			case 2: return "Remitente";
		}
		
		return null;
	}

	@Override
	public int getRowCount() {
			return inbox.getListaDeMensajes().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Mensaje email = inbox.getListaDeMensajes().get(rowIndex);
		
		switch(columnIndex){
			case 0 : return email.getAsunto();
			case 1 : return email.getFecha();
			case 2 : return email.getRemitente();
			case 3 : return email.getDestinatario();
			case 4 : return email.getContenido();
		}
		
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}

}
