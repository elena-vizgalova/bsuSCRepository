package by.bsu.flyweighttask.flyweight;

import by.bsu.flyweighttask.entity.AbstractSymbol;
import by.bsu.flyweighttask.entity.Symbol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alena_Vizgalava
 */
public class SymbolFlyweight {
    private static SymbolFlyweight instance;
    private Map<Character, AbstractSymbol> symbols;
    
    private SymbolFlyweight(){
        symbols = new HashMap<Character, AbstractSymbol>();
    }
    
    private static void init() {
        instance = new SymbolFlyweight();
    }
    
    public static SymbolFlyweight getInstance() {
        if ( null == instance ) {
            synchronized( SymbolFlyweight.class ) {
                if ( null == instance ) {
                    init();
                }
            }
        }
        return instance;
    }
    
    public AbstractSymbol getSymbol( char character ) {
        AbstractSymbol symbol = symbols.get( character );
        
        if ( null == symbol ) {
            symbol = new Symbol( character );
            symbols.put( character, symbol );
        }
        
        return symbol;
    }
    
    
    
}
