package org.eclipse.gef.examples.flow.parts;

import java.util.*;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.graph.*;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import org.eclipse.gef.examples.flow.policies.TransitionEditPolicy;

/**
 * @author hudsonr
 * Created on Jul 16, 2003
 */
public class TransitionPart extends AbstractConnectionEditPart {

protected void applyGraphResults(CompoundDirectedGraph graph, Map map) {
	Edge e = (Edge)map.get(this);
	NodeList nodes = e.vNodes;
	PolylineConnection conn = (PolylineConnection)getConnectionFigure();
	conn.setTargetDecoration(new PolygonDecoration());
	e.start.x = e.source.x + 10;
	e.end.x = e.target.x + 10;
	conn.setSourceAnchor(new XYAnchor(e.start));
	conn.setTargetAnchor(new XYAnchor(e.end));
	if (nodes != null) {
		List bends = new ArrayList();
		for (int i = 0; i < nodes.size(); i++) {
			Node vn = nodes.getNode(i);
			int x = vn.x;
			int y = vn.y;
			if (e.isFeedback) {
				bends.add(new AbsoluteBendpoint(x, y + vn.height));
				bends.add(new AbsoluteBendpoint(x, y));

			} else {
				bends.add(new AbsoluteBendpoint(x, y));
				bends.add(new AbsoluteBendpoint(x, y + vn.height));
			}
		}
		conn.setRoutingConstraint(bends);
	} else {
		conn.setRoutingConstraint(Collections.EMPTY_LIST);
	}
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	installEditPolicy(
		EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	installEditPolicy(EditPolicy.CONNECTION_ROLE, new TransitionEditPolicy());
}

/**
 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
 */
protected IFigure createFigure() {
	PolylineConnection conn =(PolylineConnection)super.createFigure();
	conn.setConnectionRouter(new BendpointConnectionRouter(){
		public void route(Connection conn) {
			GraphAnimation.recordInitialState(conn);
			if (!GraphAnimation.playbackState(conn))
				super.route(conn);
		}
	});

	conn.setTargetDecoration(new PolygonDecoration());
	return conn;
}

/**
 * @see org.eclipse.gef.EditPart#setSelected(int)
 */
public void setSelected(int value) {
	super.setSelected(value);
	if (value != EditPart.SELECTED_NONE)
		((PolylineConnection)getFigure()).setLineWidth(2);
	else
		((PolylineConnection)getFigure()).setLineWidth(1);
}

public void contributeToGraph(CompoundDirectedGraph graph, Map map) {
	GraphAnimation.recordInitialState(getConnectionFigure());
	Node source = (Node)map.get(getSource());
	Node target = (Node)map.get(getTarget());
	Edge e = new Edge(this, source, target);
	e.weight = 2;
	graph.edges.add(e);
	map.put(this, e);
}

/**
 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#refreshSourceAnchor()
 */
protected void refreshSourceAnchor() {
}

/**
 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#refreshTargetAnchor()
 */
protected void refreshTargetAnchor() {
}

}
