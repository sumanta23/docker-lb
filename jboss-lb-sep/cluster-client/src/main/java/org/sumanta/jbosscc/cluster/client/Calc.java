package org.sumanta.jbosscc.cluster.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sumanta.jbosscc.api.RemoteStateless;

@WebServlet(value = "/Calc", name = "Calc")
public class Calc extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calc() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            RemoteEJBClient rec=new RemoteEJBClient();
            
            String jndi = "ejb:cluster-ear/cluster/ClusteredStatelessBean!org.sumanta.jbosscc.api.RemoteStateless";
            RemoteStateless ejb = rec.locateEJB(jndi);
            
            //RemoteStateless ejb = rec.locateEJBStateless(RemoteStateless.class, "cluster-ear", "cluster", "ClusteredStatelessBean", "");

            response.getWriter().append("nodenamd:" + ejb.getNodeName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
