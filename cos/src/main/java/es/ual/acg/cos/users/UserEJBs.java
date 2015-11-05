package es.ual.acg.cos.users;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.ual.acg.cos.modules.DMM;
import es.ual.acg.cos.modules.IMM;
import es.ual.acg.cos.modules.LRMM;
import es.ual.acg.cos.modules.TMM;

import javax.naming.Context;

import org.jboss.logging.Logger;


public class UserEJBs {
	
	private LRMM lrmm = null;
	private TMM tmm = null;	
	private List<DMM> dmms = null;
	private IMM imm = null;
	
	private static final Logger LOGGER = Logger.getLogger(UserEJBs.class);
	
	public UserEJBs(){
		Context initialContext;
		try
		{
			initialContext = new InitialContext();

			DMM dmm = null;			
			
			try {
				lrmm = (LRMM)initialContext.lookup("java:module/LRMM");
				tmm = (TMM)initialContext.lookup("java:module/TMM");
				dmms = new ArrayList<DMM>();
				dmm = (DMM)initialContext.lookup("java:module/DMM");
				imm = (IMM)initialContext.lookup("java:module/IMM");
				//lrmm.initialize(userID);
				//dmm.setCAM(lrmm.getCAM());
				//dmm.initializeOrUpdate();
				dmms.add(dmm);
				//tmm.setCAM(dmm.getCAM());
			} catch (NamingException e) {
				LOGGER.error(e.getMessage());
			} 
		}catch (NamingException e){
			e.printStackTrace();
		}
	}
	
	public LRMM getLRMM(){
		return lrmm;
	}
	
	public TMM getTMM(){
		return tmm;
	}
	
	public List<DMM> getDMMS(){
		return dmms;
	}
	
	public IMM getIMM(){
		return imm;
	}
	
}
