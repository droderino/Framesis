package framesis.webclient;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;


@ManagedBean
@SessionScoped
public class Bean implements Serializable {

    private String selected;
    private String result;

    public void submit() {
    }

    public void listener(AjaxBehaviorEvent event) {
        System.out.println("listener");
        result = "called by " + event.getComponent().getClass().getName();
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getResult() {
        return result;
    }
}
