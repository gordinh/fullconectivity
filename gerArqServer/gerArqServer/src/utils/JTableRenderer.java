/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Siloe
 */
public class JTableRenderer extends DefaultTableCellRenderer {

    protected void setValue(Object value) {
        if (value instanceof ImageIcon) {
            if (value != null) {
                ImageIcon d = (ImageIcon) value;
                setIcon(d);
            } else {
                setText("");
                setIcon(null);
            }
        } else {
            super.setValue(value);
        }
    }
}