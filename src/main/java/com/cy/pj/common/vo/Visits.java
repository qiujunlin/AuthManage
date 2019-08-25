package com.cy.pj.common.vo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =true)
@ToString
public class Visits {
   private Integer dianzhan;
   private Integer  daliyVisit;
   private Integer  allVisit;
   private Integer ipCount;   
}
