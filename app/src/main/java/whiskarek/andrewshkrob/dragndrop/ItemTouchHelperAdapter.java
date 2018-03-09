package whiskarek.andrewshkrob.dragndrop;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(final int fromPosition, final int toPosition);

    void onItemDismiss(final int position);
}
