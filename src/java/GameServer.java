import java.io.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


//@WebServlet(urlPatterns = {"/GameServer"})

public final class GameServer extends HttpServlet {

    private Board board = new Board();
    private Players players = new Players();
    private int playerNo = 0;
    boolean playersReady = false;

    @Override
    public void init(final ServletConfig config) {
        board.intGameBoard();
        players.initPlayers();
        Logger.getGlobal().log(Level.INFO, "Started Game");
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        int current = 0;
        Object pl;
        pl = session.getAttribute("player");

        if (pl == null) {
            if (playerNo < 5) {   //only 4 sessions get saved
                playerNo++;
                if(playerNo==4){  //if no of players are 4
                    players.playersReady=true; //4 players connected, players can move
                    players.canMove = true;
                    players.initPlayers();
                }
                session.setAttribute("player", playerNo + "");
                current = playerNo;
            }
        } else {
            current = Integer.parseInt((String) session.getAttribute("player"));
        }
        String movement = request.getParameter("keypress");
        synchronized (players) {
            players.move(current, Integer.parseInt(movement),board);  //call the move , pass the player and movement and board
            players.gotPoints(board, current);     //check if that move got any point
            players.notifyAll();
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream;charset=UTF-8");
        Object pl;
        int current;

        try (final PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            pl = session.getAttribute("player");

            if (pl == null) {
                if (playerNo < 5) {
                    playerNo++;
                    if(playerNo==4){
                    players.playersReady=true; //4 players connected, players can move
                    players.canMove = true;
                    players.initPlayers();
                }
                    session.setAttribute("player", playerNo + "");
                    current = playerNo;
                }
            } else {
                current = Integer.parseInt((String) session.getAttribute("player"));
            }
            while (!Thread.interrupted()) {
                synchronized (players) {
                    out.println("data:{" + board.printBoard() + ", " + players.printPlayers() + "}");
                    out.println();
                    out.flush();
                    players.wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {

    }

}
