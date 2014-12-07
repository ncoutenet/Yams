/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.pojos;

import java.awt.Toolkit;
 
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author nicolas
 */
public class LimitedTextField extends JTextField {
    private class TextLimiter extends DocumentFilter {
        private int max;

        public TextLimiter(int max) {
            this.max = max;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + str.length();

            if (newLength <= max){
                fb.replace(offset, length, str, attrs);
            } 
            else{
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    
    public LimitedTextField(int maxLength)
    {
    super();
    AbstractDocument doc = (AbstractDocument) getDocument();
    doc.setDocumentFilter(new TextLimiter(maxLength));
    }
 
}