package data.repos;

import model.Database.Address;

public interface AddressRepository {
    public Address getAddressByID(int id);
    public void addAddress(Address address);
    public void updateAddress(Address address);
    public void deleteAddress(int id);
    
}
