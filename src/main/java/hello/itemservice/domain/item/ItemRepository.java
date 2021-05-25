package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 패키지를 분리할 수 있지만, 프로젝트가 작아서 같은 패키지에 작업을 한다.
@Repository
public class ItemRepository {

    //Item의 id가 Long타입이어서 맞췄어요
    private static final Map<Long, Item> store = new HashMap<>(); //static 사용했다는 거 주의해주세요 실제는 해쉬맵 쓰면 안됩니다
    //멀티쓰레드 환경에서 여러개가 store에 접근하게 되면 해쉬맵을 쓰시면 안되요. 쓰고 싶으면 ConcurrentHashMap을 써야한다.
    private static long sequence = 0L; //static
    //동시에 접근하면 값이 꼬일 수 있으니까 얘도 롱 쓰면 안되고 어터믹 롱 같은 다른 거 써야한다.

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        //ArrayList로 한번 감싸서 반환하게 되면, ArrayList 값을 넣어도 store는 변하지 않기 때문에 안전하게 사용.
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId); //item을 찾아서
        findItem.setItemName(updateParam.getItemName()); //찾은 거에다가
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());

        //원래는 updateParam에 대한 클래스하나 더 만드는게 맞는 일이다. 왜냐면 id가 사용이 안되잖아요 여기서
    }

    public void clearStore() {
        store.clear();  //테스트에서 쓰려고 만든거. 이거하면 해쉬맵데이터 다 날라갑니다.
    }

}
