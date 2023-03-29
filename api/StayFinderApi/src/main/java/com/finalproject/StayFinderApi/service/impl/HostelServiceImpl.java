package com.finalproject.StayFinderApi.service.impl;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.HostelStatusEnum;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.PostStatusEnum;
import com.finalproject.StayFinderApi.entity.RoomType;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.repository.RoomTypeRepository;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.utils.AppConstants;
import com.finalproject.StayFinderApi.utils.AppUtils;

@Service
public class HostelServiceImpl implements IHostelService {

	@Autowired
	private HostelRepository hostelRepo;

	@Autowired
	private RoomTypeRepository roomTypeRepo;

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	public HostelResp saveHostel(HostelRequest hostelReq) {
		Hostel newHostel = new Hostel(hostelReq.getName(), hostelReq.getCapacity(), hostelReq.getArea(),
				hostelReq.getAddress(), hostelReq.getRentPrice(), hostelReq.getDepositPrice(), hostelReq.getStatus(),
				hostelReq.getDescription(), hostelReq.getElectricPrice(), hostelReq.getWaterPrice());
		
		RoomType roomType = roomTypeRepo.findById(hostelReq.getRoomTypeId()).orElseThrow(()->new NotFoundException("Không tồn tại roomtypeId"));
		newHostel.setRoomtype(roomType);

		Hostel hostel = hostelRepo.save(newHostel);

		if (!hostelReq.getImages().isEmpty()) {
			hostelReq.getImages().forEach(img -> {
				img.setHostel(hostel);
				imageRepo.save(img);
			});
		}

		Post post = new Post();
		Optional<Account> accountOptional = accountRepo.findById(hostelReq.getPost().getAccountId());
		if (accountOptional.isPresent())
			post.setAccount(accountOptional.get());
		else {
			throw new RuntimeException("Can't find account by id: " + hostelReq.getPost().getAccountId());
		}
		post.setAccount(accountRepo.findById(hostelReq.getPost().getAccountId()).get());
		post.setContent(hostelReq.getPost().getContent());
		post.setStatus(PostStatusEnum.NOT_APPROVED.getValue());
		post.setTitle(hostelReq.getPost().getTitle());
		post.setHostel(hostelRepo.findById(hostel.getId()).get());
		post.setNumberOfFavourites(0);
		post.setPostTime(new Date());
		postRepo.save(post);

		Hostel h = hostelRepo.findById(hostel.getId()).get();

		return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(), h.getRentPrice(),
				h.getDepositPrice(), h.getStatus(), h.getDescription(), h.getRoomtype(), h.getElectricPrice(),
				h.getWaterPrice(), post, imageRepo.findByHostelId(h.getId()));
	}

	@Override
	public HostelResp updateHostel(HostelRequest hostel) {
		Optional<Hostel> optional = hostelRepo.findById(hostel.getId());
		if (optional.isPresent()) {
			Hostel newHostel = optional.get();
			newHostel.setAddress(hostel.getAddress());
			newHostel.setArea(hostel.getArea());
			newHostel.setCapacity(hostel.getCapacity());
			newHostel.setDepositPrice(hostel.getDepositPrice());
			newHostel.setDescription(hostel.getDescription());
			newHostel.setName(hostel.getName());
			newHostel.setRentPrice(hostel.getRentPrice());
			newHostel.setWaterPrice(hostel.getWaterPrice());
			newHostel.setRoomtype(roomTypeRepo.findById(hostel.getRoomTypeId()).get());
			
			
			if (!hostel.getImages().isEmpty()) {
				hostel.getImages().forEach(img -> {
					img.setHostel(newHostel);
					imageRepo.save(img);
				});
			}
			Hostel h = hostelRepo.save(newHostel);
			
			Post post = postRepo.findById(hostel.getId()).get();
			post.setContent(hostel.getPost().getContent());
			post.setTitle(hostel.getPost().getTitle());
			postRepo.save(post);
			
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(), h.getRoomtype(),
					h.getElectricPrice(), h.getWaterPrice(), h.getPost(), imageRepo.findByHostelId(h.getId()));
		} else {
			throw new RuntimeException("Can't update Hostel, can't find hostel by id: " + hostel.getId());
		}
	}

	@Override
	public void deleteHostel(Long id) {
		imageRepo.findByHostelId(id).forEach(item -> imageRepo.deleteById(item.getId()));
		postRepo.deleteById(id);
		hostelRepo.deleteById(id);
	}

	@Override
	public PagedResponse <Hostel> getAllHostel(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostels = hostelRepo.findAll(pageable);
		
		
		List<Hostel> photoResponses = new ArrayList<>(hostels.getContent().size());
		photoResponses.addAll(hostels.getContent());
		return new PagedResponse<>(photoResponses, hostels.getNumber(), hostels.getSize(), hostels.getTotalElements(),
				hostels.getTotalPages(), hostels.isLast());
//				.stream().map(h -> {
//			HostelResp hostelResp = new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
//					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(), h.getRoomtype(),
//					h.getElectricPrice(), h.getWaterPrice(), h.getPost(), h.getImages());
//			return hostelResp;
//		}).collect(Collectors.toList());
//		Collections.sort(r, new Comparator<HostelResp>() {
//			@Override
//			public int compare(HostelResp o1, HostelResp o2) {
//				return o2.getPost().getPostTime().compareTo(o1.getPost().getPostTime());
//			}
//		});
//		return r;
		
	}

	public List<Hostel> getAll() {
		return hostelRepo.findAll();
	}

	@Override
	public HostelResp getHostelRespById(Long id) {
		Optional<Hostel> hostel = hostelRepo.findById(id);
		if (hostel.isPresent()) {
			Hostel h = hostel.get();
			HostelResp hostelResp = new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(), h.getRoomtype(),
					h.getElectricPrice(), h.getWaterPrice(), h.getPost(), h.getImages());
			return hostelResp;
		} else
			return null;
	}

	@Override
	public Hostel getHostelByPostId(long id) {

		return hostelRepo.findByPostId(id);
	}


	@Override
	public PagedResponse<Hostel> findByManyOption(int page, int size, String address, double areaMin, double areMax, double minRent, double maxRent,
			int capacity, long idRoomType) {
		
		List<Hostel> r = hostelRepo.findByStatus(HostelStatusEnum.NO.getValue()).stream()
				.filter(item -> item.getAddress().toLowerCase().contains(address.toLowerCase())
						&& item.getArea() >= areaMin && item.getArea() <= areMax && item.getRentPrice() >= minRent
						&& item.getRentPrice() <= maxRent && item.getCapacity() <= capacity && item.getPost().getStatus() == PostStatusEnum.APPROVED.getValue())
				.collect(Collectors.toList());
		if (idRoomType == 0) {
			Collections.sort(r, new Comparator<Hostel>() {
				@Override
				public int compare(Hostel o1, Hostel o2) {
					return o2.getPost().getPostTime().compareTo(o1.getPost().getPostTime());
				}
			});
			return null;
		}
			
		else {
			r=  r.stream().filter(item -> item.getRoomtype().getId() == idRoomType).collect(Collectors.toList());
			Collections.sort(r, new Comparator<Hostel>() {
				@Override
				public int compare(Hostel o1, Hostel o2) {
					return o2.getPost().getPostTime().compareTo(o1.getPost().getPostTime());
				}
			});
			return null;
			
		}
	}

	@Override
	public HostelResp updateStatusHostel(long id, int status) {
		Optional<Hostel> hostel = hostelRepo.findById(id);
		if (hostel.isPresent()) {
			Hostel h = hostel.get();
			h.setStatus(status);
			h = hostelRepo.save(h);
			HostelResp hostelResp = new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(), h.getRoomtype(),
					h.getElectricPrice(), h.getWaterPrice(), h.getPost(), h.getImages());
			return hostelResp;
		} else
			return null;
	}

	@Override
	public PagedResponse<Hostel> getHostelByStatus(int page, int size, int status) {
//		return hostelRepo.findByStatus(status);
		return null;
	}

	@Override
	public PagedResponse<Hostel> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
}
