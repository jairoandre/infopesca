/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.ui.component.behavior;

import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;

/**
 *
 * @author root
 */
@FacesBehavior("infopesca.behavior.ClientSideValidation")
public class ClientSideValidation extends ClientBehaviorBase {

    private String form = "form";
    private Boolean reset = false;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {
        if (!reset) {
            return "if($('#" + form + "').validate().form()){"
                    + "return true;}"
                    + "else{"
                    + "return false;}";
        }else{
            return "$('#" + form + "').validate().form(); return true;";
        }
    }
}
