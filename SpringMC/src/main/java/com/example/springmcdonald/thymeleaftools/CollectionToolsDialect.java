/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.thymeleaftools;

import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 *
 * @author Nathan
 */

public class CollectionToolsDialect implements IExpressionObjectDialect{
    //The name you want to use in Thymeleaf
    private static final String COLLECTION_TOOLS_EXPRESSION_NAME = "collectiontools";
    //Name management Set
    private static final Set<String> ALL_EXPRESSION_NAMES = new HashSet<String>(){
        {add(COLLECTION_TOOLS_EXPRESSION_NAME);}
            };
    

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory(){
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return ALL_EXPRESSION_NAMES;
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                //Associate the name with the instance of your own Utility
                if(expressionObjectName.equals(COLLECTION_TOOLS_EXPRESSION_NAME)){
                    return new CollectionTool();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                //Implemented as needed
                return true;
            }
            
        };
    }

    @Override
    public String getName() {
        return "CollectionTool";
    }


    
}
