package com.lnet.ums.service;

import com.lnet.framework.core.ListResponse;
import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.framework.util.Snowflake;
import com.lnet.ums.contract.api.PriceService;
import com.lnet.model.ums.customer.customerDto.PriceCalcDto;
import com.lnet.model.ums.customer.customerDto.PriceDto;
import com.lnet.model.ums.customer.customerDto.PriceRangeDto;
import com.lnet.ums.mapper.dao.PriceRangeDao;
import com.lnet.ums.mapper.dao.TransportationPriceDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/21.
 */
@Service
@Slf4j
@Transactional
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRangeDao priceRangeDao;

    @Autowired
    TransportationPriceDao transportationPriceDao;
    @Override
    public Response<PriceDto> create(PriceDto priceDto) {
        try{
            Assert.notNull(priceDto);
            priceDto.setPriceId(Snowflake.getInstance().next());
            priceDto.getRangeDto().forEach(range->{
                range.setPriceId(priceDto.getPriceId());
                range.setRangeId(Snowflake.getInstance().next());
            });
            int a = transportationPriceDao.create(priceDto);
            int b =priceRangeDao.batchCreate(priceDto.getRangeDto());
            if(a>0&&b>0){
                return ResponseBuilder.success(priceDto);
            }else {
                return ResponseBuilder.fail("新增报价失败");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<List<PriceDto>> batchCreate(List<PriceDto> priceDtos) {
        try{
            Assert.notEmpty(priceDtos);
            List<PriceRangeDto> ranges = new ArrayList<>();
            for (PriceDto priceDto : priceDtos) {
                priceDto.setPriceId(Snowflake.getInstance().next());
                priceDto.getRangeDto().forEach(range->{
                    range.setPriceId(priceDto.getPriceId());
                    range.setRangeId(Snowflake.getInstance().next());
                });
                ranges.addAll(priceDto.getRangeDto());
            }
            int a = transportationPriceDao.batchCreate(priceDtos);
            int b =priceRangeDao.batchCreate(ranges);
            if(a>0&&b>0){
                return ResponseBuilder.success(priceDtos);
            }else {
                return ResponseBuilder.fail("新增报价失败");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<PriceDto> update(PriceDto priceDto) {
        try{
            Assert.notNull(priceDto);
            int a = transportationPriceDao.update(priceDto);
            int d = priceRangeDao.deleteByPriceId(priceDto.getPriceId());
            int b =priceRangeDao.batchCreate(priceDto.getRangeDto());
            if(a>0&&d>=0&&b>0){
                return ResponseBuilder.success(priceDto);
            }else {
                return ResponseBuilder.fail("修改报价失败");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<PriceDto> get(String priceId) {
        PriceDto priceDto = transportationPriceDao.get(priceId);
        List<PriceRangeDto> ranges = priceRangeDao.findByPriceId(priceId);
        return null;
    }

    @Override
    public PageResponse<PriceDto> pageList(int pageNumber, int pageSize, Map<String, Object> params) {
        List<PriceDto> list = transportationPriceDao.search(params);
        return null;
    }

    @Override
    public ListResponse<PriceDto> findPrice(PriceDto dto) {
        List<PriceDto> list = transportationPriceDao.findPrice(dto);
        return null;
    }

    @Override
    public ListResponse<PriceRangeDto> findByPriceId(String priceId) {
        try{
            List<PriceRangeDto> ranges = priceRangeDao.findByPriceId(priceId);
            return ResponseBuilder.list(ranges);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public Response<PriceRangeDto> findByPriceId(String priceId, BigDecimal unit) {
        try{
            PriceRangeDto range= priceRangeDao.findByPriceId(priceId,unit);
            return ResponseBuilder.success(range);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public ListResponse<PriceDto> findByPrice(String ownerCode, String calcAttr, String transportType) {
        try{
            List<PriceDto> ranges = transportationPriceDao.findByPrice(ownerCode,calcAttr,transportType);
            return ResponseBuilder.list(ranges);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseBuilder.listFail(e);
        }
    }

    public Response<Map<String, BigDecimal>> calculate(PriceCalcDto priceCalcDto) {
        try {
            Map<String, BigDecimal> result = new HashMap<>();
            String ownerCode = priceCalcDto.getOwnerCode();
            String calcAttr = priceCalcDto.getCalcAttr();
            String transportType = priceCalcDto.getTransportType();
            String orgin = priceCalcDto.getOrginCode();
            String dest = priceCalcDto.getDestCode();
            String vehicleType = priceCalcDto.getVehicleType();
            String productCategory = priceCalcDto.getProductCategory();
            BigDecimal value = priceCalcDto.getValue();
            Assert.hasText(ownerCode, "ownerCode 不能为null");
            Assert.notNull(calcAttr, "calcAttr 不能为null");
            Assert.hasText(transportType, "transportType 不能为null");
            Assert.hasText(orgin, "orgin 不能为null");
            Assert.hasText(dest, "dest 不能为null");
            Assert.notNull(value, "dest 不能为null");
            List<PriceDto> priceDtos = findByPrice(ownerCode, calcAttr, transportType).getBody();
            if (priceDtos != null && priceDtos.size() > 0) {
                if (!StringUtils.isEmpty(productCategory)) {
                    priceDtos = priceDtos.stream().filter(priceDto -> productCategory.equals(priceDto.getProductCategory())).collect(Collectors.toList());
                }
                if (!StringUtils.isEmpty(vehicleType)) {
                    priceDtos = priceDtos.stream().filter(priceDto -> vehicleType.equals(priceDto.getVehicleType())).collect(Collectors.toList());
                }
            }
            if (priceDtos == null || priceDtos.isEmpty()) {
                return ResponseBuilder.fail("没有匹配的报价");
            }
            Map<String, List<PriceDto>> groupedByAccount = priceDtos.stream().collect(Collectors.groupingBy(PriceDto::getExpenseAccount));
            for (Map.Entry<String, List<PriceDto>> entry : groupedByAccount.entrySet()) {
                PriceDto priceDto = filterByDistrict(entry.getValue(), orgin, dest);
                if (priceDto != null) {
                    result.put(entry.getKey(), getAmount(priceDto, value));
                }
            }
            if (result.size() > 0)
                return ResponseBuilder.success(result);
            else
                return ResponseBuilder.fail("没有匹配的报价");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    private BigDecimal getAmount(PriceDto priceDto, BigDecimal value) {
        PriceRangeDto rangeDto = findByPriceId(priceDto.getPriceId(), value).getBody();
        BigDecimal amount = new BigDecimal(0);
        if (rangeDto != null) {
            /*add 加  subtract 减    multiply 乘 divide除
        RANGED("区间"),
        INCREASED("续增累加"),
        UNITED("乘积");*/
            BigDecimal unit = rangeDto.getUnitPrice() == null ? new BigDecimal(0) : rangeDto.getUnitPrice();
            switch (priceDto.getCalcFormula()) {
                case INCREASED://续增
                    if (value.compareTo(rangeDto.getRangeStart()) > 0) {
                        amount = rangeDto.getMinAmount().add(value.subtract(rangeDto.getRangeStart()).multiply(unit));
                    } else {
                        amount = rangeDto.getMinAmount();
                    }
                    break;
                case RANGED:
                    amount = value.multiply(unit);
                    amount = amount.compareTo(rangeDto.getMinAmount()) > 0 ? amount : rangeDto.getMinAmount();
                    break;
                case UNITED:
                    amount = value.multiply(unit);
                    break;
            }
        }
        return amount;
    }

    /**
     * 根据起始地目的地过滤,详细地址查询不到时默认查询上级地址
     *
     * @param priceDtos
     * @param orgin
     * @param dest
     * @return
     */
    private PriceDto filterByDistrict(List<PriceDto> priceDtos, String orgin, String dest) {
        Integer[] length = new Integer[]{2, 4, 6, 9};
        for (int i = 0; i < length.length; i++) {
            final int finalI = length[i];
            if (finalI <= orgin.length()) {
                for (int j = 0; j < length.length; j++) {
                    final int finalJ = length[j];
                    if (finalJ <= dest.length()) {
                        List<PriceDto> result = new ArrayList<>();
                        result = priceDtos.stream().filter(priceDto ->
                                orgin.substring(0, finalI).equals(priceDto.getOrgin().substring(0, finalI))
                                        && dest.substring(0, finalJ).equals(priceDto.getDestination().substring(0, finalJ))
                        )
                                .collect(Collectors.toList());

                        if (result.size() == 1) {
                            return result.get(0);
                        }
                    }
                }
            }
        }
        return null;
    }
/*

    @Resource
    TransportationPriceRepository priceRepository;
    @Resource
    PriceRangeRepository rangeRepository;

    @Override
    public Response<PriceDto> create(PriceDto priceDto) {
        try {
            Assert.notNull(priceDto);
            Assert.notEmpty(priceDto.getRangeDto());
            Assert.hasText(priceDto.getOrgin());
            Assert.hasText(priceDto.getDestination());
            Response r = validateRange(priceDto);
            if (!r.isSuccess()) {
                return r;
            }
            TransportationPrice price = PriceConverter.convertToEntity(priceDto);
            price.setPriceId(Snowflake.getInstance().next());
            price.getRanges().forEach(range -> {
                range.setRangeId(Snowflake.getInstance().next());
            });
            priceRepository.save(price);
            priceDto = PriceConverter.convertToDto(price);
            return ResponseBuilder.success(priceDto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    private Response validateRange(PriceDto priceDto) {
        for (int i = 0; i < priceDto.getRangeDto().size(); i++) {
            PriceRangeDto range = priceDto.getRangeDto().get(i);
            if (range.getRangeEnd() != null && range.getRangeStart().compareTo(range.getRangeEnd()) > 0) {
                return ResponseBuilder.fail("第" + (i + 1) + "行,区间最大值必须大于最小值");
            }
            for (int j = 0; j < priceDto.getRangeDto().size(); j++) {
                PriceRangeDto r = priceDto.getRangeDto().get(j);
                if (i == j) continue;//跳过当前
                if (range.getRangeStart().compareTo(r.getRangeStart()) > 0 && ((r.getRangeEnd() == null) || range.getRangeStart().compareTo(r.getRangeEnd()) < 0)) {
                    return ResponseBuilder.fail("第" + (i + 1) + "行,区间已包含在第" + (j + 1) + "行");
                }
                if (range.getRangeEnd() != null && range.getRangeEnd().compareTo(r.getRangeStart()) > 0 && ((r.getRangeEnd() == null) || range.getRangeEnd().compareTo(r.getRangeEnd()) < 0)) {
                    return ResponseBuilder.fail("第" + (i + 1) + "行,区间已包含在第" + (j + 1) + "行");
                }
            }
        }
        List<PriceDto> prices = findPrice(priceDto).getBody();
        if (prices != null && prices.size() > 0) {
            return ResponseBuilder.fail("该报价已存在");
        }
        return ResponseBuilder.success();
    }

    @Override
    public Response<List<PriceDto>> batchCreate(List<PriceDto> priceDtos) {
        try {
            Assert.notEmpty(priceDtos);
            List<PriceDto> success = new ArrayList<>();
            String errorMsg = "";
            for (PriceDto priceDto : priceDtos) {
                Response r = validateRange(priceDto);
                if (!r.isSuccess()) {
                    errorMsg += r.getMessage();
                } else {
                    success.add(priceDto);
                }
            }
            List<TransportationPrice> prices = PriceConverter.convertToEntity(success);
            prices.forEach(price -> {
                price.setPriceId(Snowflake.getInstance().next());
                price.getRanges().forEach(range -> {
                    range.setRangeId(Snowflake.getInstance().next());
                });
            });

            priceRepository.save(prices);
            priceDtos = PriceConverter.convertToDto(prices);
            return ResponseBuilder.success(priceDtos, errorMsg);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<PriceDto> update(PriceDto priceDto) {
        try {
            Assert.notNull(priceDto);
            Assert.notEmpty(priceDto.getRangeDto());
            Assert.hasText(priceDto.getPriceId());
            Assert.hasText(priceDto.getOrgin());
            Assert.hasText(priceDto.getDestination());
            Response r = validateRange(priceDto);
            if (!r.isSuccess()) {
                return r;
            }
            TransportationPrice price = PriceConverter.convertToEntity(priceDto);
            priceRepository.save(price);
            priceDto = PriceConverter.convertToDto(price);
            return ResponseBuilder.success(priceDto);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public Response<PriceDto> get(String priceId) {
        try {
            Assert.hasText(priceId);
            TransportationPrice price = priceRepository.findOne(priceId);
            List<PriceRange> ranges = rangeRepository.findByPriceIdOrderByRangeStart(priceId);
            price.setRanges(ranges);
            return ResponseBuilder.success(PriceConverter.convertToDto(price));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public PageResponse<PriceDto> pageList(int pageNumber, int pageSize, Map<String, Object> params) {

        try {
            Sort sort = null;
            if (!StringUtils.isEmpty(params.get("orderBy"))) {
                String[] s = params.get("orderBy").toString().split(" ");
                sort = new Sort(s[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, UnderscoresNameStrategy.columnToProperty(s[0]));
            }
            Page<TransportationPrice> pageInfo = priceRepository.findAll(new Specification<TransportationPrice>() {
                @Override
                public Predicate toPredicate(Root<TransportationPrice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Expression> expressions = new ArrayList<Expression>();
                    if (!StringUtils.isEmpty(params.get("orgin"))) {//起始地
                        expressions.add(cb.like(root.get("orgin"), params.get("orgin").toString().replaceAll("0000", "") + "%"));
                    }
                    if (!StringUtils.isEmpty(params.get("destination"))) {//目的地
                        expressions.add(cb.like(root.get("destination"), params.get("destination").toString().replaceAll("0000", "") + "%"));
                    }
                    if (!StringUtils.isEmpty(params.get("branchCode"))) {
                        expressions.add(cb.equal(root.get("branchCode"), params.get("branchCode")));
                    }
                    if (!StringUtils.isEmpty(params.get("ownerCode"))) {
                        expressions.add(cb.equal(root.get("ownerCode"), params.get("ownerCode")));
                    }
                    if (!StringUtils.isEmpty(params.get("ownerType"))) {
                        expressions.add(cb.equal(root.get("ownerType"), params.get("ownerType")));
                    }
                    if (expressions.size() > 0) {
                        return cb.and(expressions.toArray(new Predicate[expressions.size()]));
                    }
                    return null;
                }
            }, new PageRequest(pageNumber - 1, pageSize, sort));

            ConvertUtils.register(new EnumConverter(), PriceDto.CalcFormula.class);
            ConvertUtils.register(new EnumConverter(), PriceDto.CalcAttr.class);
            ConvertUtils.register(new EnumConverter(), PriceDto.OwnerType.class);
            return ResponseBuilder.page(PriceConverter.convertList(pageInfo.getContent(), PriceDto.class), pageNumber, pageInfo.getSize(), pageInfo.getTotalElements());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.pageFail(e);
        }
    }

    @Override
    public ListResponse<PriceDto> findPrice(PriceDto dto) {
        try {
            List<TransportationPrice> prices = priceRepository.findAll((root, criteriaQuery, cb) -> {
                List<Expression> expressions = new ArrayList<Expression>();
                expressions.add(cb.equal(root.get("ownerCode"), dto.getOwnerCode()));
                expressions.add(cb.equal(root.get("orgin"), dto.getOrgin()));
                expressions.add(cb.equal(root.get("destination"), dto.getDestination()));
                if (null != dto.getCalcAttr()) {
                    expressions.add(cb.equal(root.get("calcAttr"), dto.getCalcAttr().name()));
                }
                if (null != dto.getExpenseAccount()) {
                    expressions.add(cb.equal(root.get("expenseAccount"), dto.getExpenseAccount()));
                }
                if (null != dto.getTransportType()) {
                    expressions.add(cb.equal(root.get("transportType"), dto.getTransportType()));
                }
                if (null != dto.getProductCategory()) {
                    expressions.add(cb.equal(root.get("productCategory"), dto.getProductCategory()));
                }
                if (null != dto.getVehicleType()) {
                    expressions.add(cb.equal(root.get("vehicleType"), dto.getVehicleType()));
                }
                return cb.and(expressions.toArray(new Predicate[expressions.size()]));
            });
            return ResponseBuilder.list(PriceConverter.convertToDto(prices));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public ListResponse<PriceRangeDto> findByPriceId(String priceId) {
        try {
            List<PriceRange> priceRanges = rangeRepository.findByPriceIdOrderByRangeStart(priceId);
            return ResponseBuilder.list(PriceConverter.convertList(priceRanges, PriceRangeDto.class));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.listFail(e);
        }
    }

    @Override
    public Response<PriceRangeDto> findByPriceId(String priceId, BigDecimal unit) {
        try {
            Assert.notNull(unit);
            List<PriceRange> priceRanges = rangeRepository.findByPriceIdOrderByRangeStart(priceId);
            //最大值如果是null  则表示无限大
            PriceRange range = priceRanges.stream().filter(priceRange ->
                    priceRange.getRangeStart().compareTo(unit) <= 0 &&
                            (priceRange.getRangeEnd() == null || priceRange.getRangeEnd().compareTo(unit) > 0)).findFirst().get();
            return ResponseBuilder.success(BeanHelper.convert(range,PriceRangeDto.class));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.fail(e);
        }
    }

    @Override
    public ListResponse<PriceDto> findByPrice(String ownerCode, String calcAttr, String transportType) {
        try {
            Assert.hasText(ownerCode);
            List<TransportationPrice> prices = priceRepository.findByOwnerCodeAndCalcAttrAndTransportType(ownerCode,calcAttr,transportType);
            return ResponseBuilder.list(PriceConverter.convertToDto(prices));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseBuilder.listFail(e);
        }
    }
*/
}
