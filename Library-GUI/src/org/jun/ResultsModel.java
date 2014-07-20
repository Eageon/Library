package org.jun;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ResultsModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8793693843353517382L;
	String[] columnNames = new String[0];
	@SuppressWarnings("rawtypes")
	Vector dataRows; // Empty vector of rows
	
	/**
	 * Set the Model of table
	 * @param results
	 * @return number of columns
	 */
	public int setResultSetModel(ResultSet results) {
		if (results == null) {
			columnNames = new String[0]; // Reset the columns names
			dataRows.clear(); // Remove all entries in the Vector
			fireTableChanged(null); // Tell the table there is new model data
			return 0;
		}
		
		ResultSetMetaData metadata;
		try {
			metadata = results.getMetaData();
			int columns = metadata.getColumnCount(); // Get number of columns
			columnNames = new String[columns]; // Array to hold names

			// Get the column names
			for (int i = 0; i < columns; i++) {
				columnNames[i] = metadata.getColumnLabel(i + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnNames.length;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setResultSet(ResultSet results) {

		if (results == null) {
			columnNames = new String[0]; // Reset the columns names
			dataRows.clear(); // Remove all entries in the Vector
			fireTableChanged(null); // Tell the table there is new model data
			return;
		}
		
		int columns = setResultSetModel(results);

		try {
			
			// Get all rows.
			dataRows = new Vector(); // New Vector to store the data
			String[] rowData; // Stores one row
			while (results.next()) {
				rowData = new String[columns]; // create array to hold the data
				for (int i = 0; i < columns; i++) {
					// For each column
					rowData[i] = results.getString(i + 1); // retrieve the data
				} // item
				dataRows.addElement(rowData); // Store the row in the vector
			}

			fireTableChanged(null); // Signal the table there is new model data
		} catch (SQLException sqle) {
			System.err.println("Errors in setResultSet(): " + sqle);
		}

	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (dataRows == null)
			return 0;
		else
			return dataRows.size();
	}

	public Object getValueAt(int row, int column) {
		return ((String[]) (dataRows.elementAt(row)))[column];
	}

	public String getColumnName(int column) {
		return columnNames[column] == null ? "No Name" : columnNames[column];
	}

	public void removeRow(int row) {
		dataRows.removeElementAt(row);
		fireTableChanged(null);
    }
}
