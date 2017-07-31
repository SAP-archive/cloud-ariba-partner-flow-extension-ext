package com.sap.cloud.samples.ariba.partner.flow.daos;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.persistency.entities.Asn;

/**
 * ASN DAO.
 *
 */
public class AsnDao extends BaseDao<Asn> {

	private static final String DEBUG_MSG_SEARCHING_FOR_ASN_FOR_EVENT_WITH_EVENT_ID = "Searching for ASN for event with event ID: [{}]";
	private static final String DEBUG_MSG_ASNS_FOUND_FOR_EVENT_WITH_EVENT_ID = "ASNS found for event with event ID: [{}]";

	private static final Logger logger = LoggerFactory.getLogger(AsnDao.class);

	public AsnDao() {
		super(Asn.class);
	}

	/**
	 * Retrieves ASN related to an event by event id.
	 * 
	 * @param eventId
	 *            the event id for the related ASN.
	 * @return the ASN or null if there is no ASN.
	 */
	public Asn findByEvent(String eventId) {
		logger.debug(DEBUG_MSG_SEARCHING_FOR_ASN_FOR_EVENT_WITH_EVENT_ID, eventId);

		TypedQuery<Asn> q = createNamedQuery(Asn.QUERY_FIND_BY_EVENT_ID);
		q.setParameter(Asn.QUERY_PARAM_FIND_BY_EVENT_ID, eventId);

		List<Asn> resultList = q.getResultList();
		logger.debug(DEBUG_MSG_ASNS_FOUND_FOR_EVENT_WITH_EVENT_ID, resultList);

		return resultList.size() == 0 ? null : resultList.get(0);
	}

}
