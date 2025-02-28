package org.swordapp.server.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swordapp.server.CollectionAPI;
import org.swordapp.server.CollectionDepositManager;
import org.swordapp.server.CollectionListManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CollectionServletDefault extends SwordServlet {
    private static final long serialVersionUID = 4652441906004204311L;
    private static Logger log = LoggerFactory.getLogger(CollectionServletDefault.class);
    
    protected transient CollectionAPI api;

    public void init() throws ServletException {
        super.init();

        // load the collection list manager implementation
        Object possibleClm = this.loadImplClass("collection-list-impl", true); // allow null
        CollectionListManager clm = possibleClm == null ? null : (CollectionListManager) possibleClm;

        // load the deposit manager implementation
        CollectionDepositManager cdm = (CollectionDepositManager) this.loadImplClass("collection-deposit-impl", false);

        // load the API
        this.api = new CollectionAPI(clm, cdm, this.config);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.api.get(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.api.post(req, resp);
    }
}
