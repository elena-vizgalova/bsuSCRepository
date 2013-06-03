package by.bsu.flyweighttask.runner;

import by.bsu.flyweighttask.entity.AbstractSymbol;
import by.bsu.flyweighttask.entity.Point;
import by.bsu.flyweighttask.entity.SymbolContext;
import by.bsu.flyweighttask.flyweight.SymbolFlyweight;

public class Runner {

    public static void main(String[] args) {
        
        SymbolFlyweight symbolFlyweight = SymbolFlyweight.getInstance();
        AbstractSymbol symbol = symbolFlyweight.getSymbol( 'c' );
        
        Point point = new Point();
        point.setxCoordinate( 1 );
        point.setyCoordinate( 2 );
        
        SymbolContext context = new SymbolContext();
        
        symbol.setFont("Arial", context);
        symbol.setSize(14, context);
        
        symbol.draw( context, point );
        
        AbstractSymbol symbol1 = symbolFlyweight.getSymbol( 'c' );
        
        Point point1 = new Point();
        point1.setxCoordinate( 2 );
        point1.setyCoordinate( 3 );
        
        SymbolContext context1 = new SymbolContext();
        
        symbol1.setFont("Monospaced", context1);
        symbol1.setSize(16, context1);
        
        symbol1.draw( context1, point1 );
    }
}
