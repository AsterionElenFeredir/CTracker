package main;

import gui.CTracker;
import model.Actor;
import model.ActorList;
import utils.Constants;

public class Main {

	public static void main(String[] args) {
		ActorList actorList = new ActorList();
		
		if(Constants.TEST_MODE == 0)
		{
			Actor a1 = new Actor( "Alduin Isenber", "images/human.jpg", 20, 6, 70);
			actorList.getActorList().add(a1);
			Actor a2 = new Actor("Alaria elwey", "images/elf.jpg", 24, 9, 60);
			actorList.getActorList().add(a2);
			Actor a3 = new Actor("Gorsh Torak", "images/orc.jpg", 18, 4, 80);
			actorList.getActorList().add(a3);

		} else {
			Actor a1 = new Actor("Test", null, 20, 6, 80);
			actorList.getActorList().add(a1);
//			Actor a2 = new Actor("Test2", null, 20, 6, 80);
//			actorList.getActorList().add(a2);
		}
		
		CTracker.getInstance().setActorList(actorList);
	}

}
