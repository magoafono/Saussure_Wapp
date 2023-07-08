package it.cnr.ilc.ga.utils;

import it.cnr.ilc.ga.model.pericope.Pericope;

import java.util.*;

public class PericopeSort {

	public static final Comparator<Pericope> 
	A_ORDER = 
		new Comparator<Pericope>() {

		public int compare(Pericope p1, Pericope p2) {
			return 	(p1.getAtext().getId() < p2.getAtext().getId() ? -1 :
				(p1.getAtext().getId() == p2.getAtext().getId() ? 0 : 1));
		}
	};

	public static final Comparator<Pericope> 
	B_ORDER = 
		new Comparator<Pericope>() {

		public int compare(Pericope p1, Pericope p2) {
			return 	(p1.getAtext().getBid() < p2.getAtext().getBid() ? -1 :
				(p1.getAtext().getBid() == p2.getAtext().getBid() ? 0 : 1));
		}
	};	


}