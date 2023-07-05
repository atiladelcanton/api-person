package br.com.zensolutions.integrationtests.vo.wrapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class WrapperPersonVO implements Serializable {

    private PersonEmbeddedVo embeddedVo;
}
