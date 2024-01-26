package fr.but.Mauritius_Loic_Booms_Amaury.Game.Map.Floor.Event;

import java.nio.file.Path;
import java.util.TreeMap;

public record Events() {

	public static final TreeMap<String, Path> List_events = new TreeMap<String,Path>() {{
		put("entrance",(Path.of("img","logo_map","entree.png")));
		put("exit",(Path.of("img","logo_map","arrivee.png")));
		put("monsters",(Path.of("img","logo_map","enemy.png")));
		put("healer",(Path.of("img","logo_map","healer.png")));
		put("merchant",(Path.of("img","logo_map","Gold.png")));
		put("treasure",(Path.of("img","logo_map","coffre.png")));
	}};
}
