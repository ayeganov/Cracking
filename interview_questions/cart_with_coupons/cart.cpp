#include <cassert>
#include <iostream>
#include <memory>
#include <vector>
#include <string>


class CartItem;
typedef std::shared_ptr<CartItem> CartItemPtr;
typedef std::vector<CartItemPtr> ItemList;


class CartItem
{
public:
    double get_price() const { return m_price; }
    double get_discount_price() { return m_discount_price; }
    void reset_discount() { m_discount_price = m_price; }
    virtual void apply_discount(const ItemList& items) = 0;
    virtual void apply_discount(double percent_off) = 0;
    const std::string& get_type() const { return m_item_type; }
    virtual ~CartItem() {}

protected:
    double m_price;
    double m_discount_price;
    std::string m_item_type;
};


class BuyableItem : public CartItem
{
public:
    BuyableItem(double price, std::string type);
    void apply_discount(const ItemList& items) {}
    void apply_discount(double percent_off);

private:
};

BuyableItem::BuyableItem(double price, std::string type)
{
    m_price = price;
    m_discount_price = price;
    m_item_type = type;
}

void BuyableItem::apply_discount(double percent_off)
{
    m_discount_price *= (1.0 - percent_off);
}

class Coupon : public CartItem
{
public:
    Coupon(double discount_rate);
    virtual ~Coupon() {}

protected:
    double m_discount_rate;
};

Coupon::Coupon(double discount_rate)
  : m_discount_rate(discount_rate)
{
    m_price = 0.0;
    m_discount_price = 0.0;
    m_item_type = "Coupon";
}


class AllItemsOffCoupon : public Coupon
{
public:
    AllItemsOffCoupon(double discount_rate) : Coupon(discount_rate) {}
    void apply_discount(const ItemList& items);
    void apply_discount(double);
};

void AllItemsOffCoupon::apply_discount(double percent_off)
{
    m_price = 0.0 * percent_off;
}

void AllItemsOffCoupon::apply_discount(const ItemList& items)
{
    for(ItemList::size_type i = 0; i < items.size(); ++i)
    {
        CartItemPtr item = items[i];
        item->apply_discount(m_discount_rate);
    }
}

class NextItemOffCoupon : public Coupon
{
public:
    NextItemOffCoupon(double discount_rate) : Coupon(discount_rate) {}
    void apply_discount(const ItemList& items);
    void apply_discount(double);
};

void NextItemOffCoupon::apply_discount(double percent_off)
{
    m_price = 0.0 * percent_off;
}

void NextItemOffCoupon::apply_discount(const ItemList& items)
{
    // find self in list first
    ItemList::size_type self_idx = 0;
    for(ItemList::size_type i = 0; i < items.size(); ++i)
    {
        CartItemPtr item = items[i];
        if(item.get() == this)
        {
            self_idx = i;
            break;
        }
    }

    // find next BuyableItem
    for(ItemList::size_type i = self_idx + 1; i < items.size(); ++i)
    {
        CartItemPtr item_ptr = items[i];
        CartItem* item = dynamic_cast<BuyableItem*>(item_ptr.get());
        if(item)
        {
            item_ptr->apply_discount(m_discount_rate);
            break;
        }
    }
}

class NthOfTypeOffCoupon : public Coupon
{
public:
    NthOfTypeOffCoupon(double discount_rate, int n, std::string type) :
        Coupon(discount_rate),
        m_nth_item(n),
        m_item_type(type) {}

    void apply_discount(const ItemList& items);
    void apply_discount(double);

private:
    ItemList::size_type m_nth_item;
    std::string m_item_type;
};

void NthOfTypeOffCoupon::apply_discount(double percent_off)
{
    m_price = 0.0 * percent_off;
}

void NthOfTypeOffCoupon::apply_discount(const ItemList& items)
{
    // find nth item of type m_item_type
    ItemList::size_type idx = 0;
    for(ItemList::size_type i = 0; i < items.size(); ++i)
    {
        CartItemPtr item = items[i];
        if(item->get_type() == m_item_type && (++idx == m_nth_item))
        {
            item->apply_discount(m_discount_rate);
            break;
        }
    }
}

class Cart
{
public:
    Cart() {}
    void empty_cart();
    void add_item(const CartItemPtr&& item);
    void add_coupon(const CartItemPtr&& coupon);
    double total_price();

private:
    ItemList m_items;
    ItemList m_coupons;

    void reset_discounts();
};

void Cart::empty_cart()
{
    m_items.clear();
    m_coupons.clear();
}

void Cart::reset_discounts()
{
    for(CartItemPtr const& item : m_items)
    {
        item->reset_discount();
    }
}

void Cart::add_item(const CartItemPtr&& item)
{
    m_items.emplace_back(item);
}

void Cart::add_coupon(const CartItemPtr&& coupon)
{
    m_coupons.emplace_back(coupon);
    m_items.emplace_back(coupon);
}


double Cart::total_price()
{
    reset_discounts();
    double total_price = 0.0;

    for(const CartItemPtr& coupon : m_coupons)
    {
        coupon->apply_discount(m_items);
    }
    for(const CartItemPtr& item : m_items)
    {
        total_price += item->get_discount_price();
    }
    return total_price;
}


int main(int argc, char* argv[])
{
    Cart cart;
    cart.add_item(std::make_shared<BuyableItem>(10.0, "stamps"));
    cart.add_coupon(std::make_shared<AllItemsOffCoupon>(0.25));
    std::cout << "Total price with 25\% off on everything " << cart.total_price() << std::endl;

    cart.empty_cart();

    cart.add_coupon(std::make_shared<NextItemOffCoupon>(0.5));
    cart.add_item(std::make_shared<BuyableItem>(30, "Stuff"));
    cart.add_item(std::make_shared<BuyableItem>(30, "More stuff"));
    std::cout << "Total price 50\% off on next item " << cart.total_price() << std::endl;

    cart.empty_cart();

    cart.add_coupon(std::make_shared<NthOfTypeOffCoupon>(0.9, 3, "stuff"));
    cart.add_item(std::make_shared<BuyableItem>(30, "stuff"));
    cart.add_item(std::make_shared<BuyableItem>(30, "stuff"));
    cart.add_item(std::make_shared<BuyableItem>(50, "stuff"));

    std::cout << "Total price 50\% off on 3rd Stuff " << cart.total_price() << std::endl;

    cart.empty_cart();

    cart.add_item(std::make_shared<BuyableItem>(10.0, "stamps"));
    cart.add_coupon(std::make_shared<NextItemOffCoupon>(0.5));
    cart.add_item(std::make_shared<BuyableItem>(50, "stuff"));
    cart.add_coupon(std::make_shared<NthOfTypeOffCoupon>(0.9, 2, "stuff"));
    cart.add_coupon(std::make_shared<AllItemsOffCoupon>(0.25));
    cart.add_item(std::make_shared<BuyableItem>(100, "stuff"));

    std::cout << "Total price with many coupons " << cart.total_price() << std::endl;
    return 0;
}
