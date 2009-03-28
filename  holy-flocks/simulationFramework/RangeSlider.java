package simulationFramework;

import java.util.Hashtable;
import javax.swing.*;

/**
 * A labeled slider that reports values within the given range, inclusive.
 * 
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public class RangeSlider extends JSlider {
	public RangeSlider(String title, int minValue, int maxValue) {
		super(minValue, maxValue, (minValue + maxValue) / 2);

		// label range reasonably
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(minValue, new JLabel(Integer.toString(minValue)));
		labelTable.put(maxValue, new JLabel(Integer.toString(maxValue)));
		setLabelTable(labelTable);
		setPaintLabels(true);
		setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(), title));
	}
}
