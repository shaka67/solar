package com.ouer.solar.location.server;

import java.util.List;

public interface ZoneMapper {

    Zone selectByPrimaryKey(Long id);

    List<Zone> listRoots();

	List<Zone> listChildren(Long zoneId);

	List<Zone> listSiblings(Long zoneId);

	Zone findParent(Long zoneId);

	List<Zone> listParents(Long zoneId);

}