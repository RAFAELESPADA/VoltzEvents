// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.menu;

public abstract class PaginatedMenu extends Menu
{
    protected int page;
    protected int maxItemsPerPage;
    protected int index;
    
    public PaginatedMenu(final PlayerMenuUtility playerMenuUtility, final int maxItemsPerPage) {
        super(playerMenuUtility);
        this.page = 0;
        this.index = 0;
        this.maxItemsPerPage = maxItemsPerPage;
    }
    
    public PaginatedMenu(final int maxItemsPerPage) {
        this.page = 0;
        this.index = 0;
        this.maxItemsPerPage = maxItemsPerPage;
    }
    
    public int getMaxItemsPerPage() {
        return this.maxItemsPerPage;
    }
}
