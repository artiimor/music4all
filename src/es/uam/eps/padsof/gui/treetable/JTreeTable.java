package es.uam.eps.padsof.gui.treetable;
import java.util.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class JTreeTable extends JXTreeTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTreeTable (List<String> fields, List<Object[]> content) {
		
		Object[] r= {"Raiz"}; 
		Node root = new Node(r);
		Boolean aux = false;
		Boolean subnode = false;
		Node node=root;

		Node myChild = null;
		for (Object[] data : content) {
			Node child = new Node(data);
			if (data.length <= 1) {
				if (((String)data[0]).contentEquals("n")) {
					subnode = true;
				} else {
					aux = true;
				}
			} else if (aux) {
				root.add(child);
				myChild = child;
				node = child;
				aux = false;
			} else if (subnode) {
				node.add(child);
				myChild = child;
				subnode = false;
			}
			else {
				myChild.add(child);
			}
		}
		
		DefaultTreeTableModel model = new DefaultTreeTableModel(root,fields);
		this.setTreeTableModel(model);
		this.setShowGrid(true,true);
		this.setColumnControlVisible(true);
		this.packAll();
	}
	
}